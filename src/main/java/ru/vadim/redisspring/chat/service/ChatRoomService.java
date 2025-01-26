package ru.vadim.redisspring.chat.service;

import org.redisson.api.RListReactive;
import org.redisson.api.RTopicReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class ChatRoomService implements WebSocketHandler {

    @Autowired
    private RedissonReactiveClient client;

    // метод выолняется только раз при коннекшене, после все происходит в пайплайне после //subscribe
    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        String room = getChatRoomName(webSocketSession);
        RTopicReactive topic = this.client.getTopic(room, StringCodec.INSTANCE); // создаем топик в редисе с ключом комнаты из юрл
        RListReactive<String> list = this.client.getList("history" + room, StringCodec.INSTANCE);
        // subscribe
        // здесь получаем сообщение из вебсокета и публикуем в топик
        webSocketSession.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(message -> list.add(message).then(topic.publish(message)))
                .doOnError(System.out::println)
                .doFinally(s -> System.out.println("Subscriber finally " + s))
                .subscribe();

        // publisher
        // здесь получаем сообщение из топика редиса
        Flux<WebSocketMessage> flux = topic.getMessages(String.class)
                .startWith(list.iterator())
                .map(webSocketSession::textMessage)
                .doOnError(System.out::println)
                .doFinally(s -> System.out.println("publisher finally " + s));

        return webSocketSession.send(flux);//и публикуем в вебсокет
    }

    private String getChatRoomName(WebSocketSession socketSession){
        URI uri = socketSession.getHandshakeInfo().getUri();
        return UriComponentsBuilder.fromUri(uri)
                .build()
                .getQueryParams()
                .toSingleValueMap()
                .getOrDefault("room", "default");
    }
}
