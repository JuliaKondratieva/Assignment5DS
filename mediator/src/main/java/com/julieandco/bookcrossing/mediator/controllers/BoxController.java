package com.julieandco.bookcrossing.mediator.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julieandco.bookcrossing.mediator.dto.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/boxes")
public class BoxController {
    private static final String URL = "http://localhost:8004";
    private static final String URLO = "http://localhost:8003";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders headers = new HttpHeaders();
    private static final HttpEntity<Object> headersEntity = new HttpEntity<>(headers);

    @PostMapping("/save")
    public ResponseEntity<Void> saveBox(@RequestBody String addressJson){
        String deliverJsonStr = addressJson;
        HttpEntity<String> deliverJsonNew = new HttpEntity<>(deliverJsonStr, headers);
        ResponseEntity<Void> response3 = restTemplate
                .exchange(URL + "/api/boxes/save", HttpMethod.POST,
                        deliverJsonNew, Void.class);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/deliver")
    public ResponseEntity<Void> deliverProducts(@RequestBody String deliverJson) {
        System.out.println("DELIVERPRODUCTS FUNCTION");
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        DeliveryDTO deliver = gson.fromJson(deliverJson, DeliveryDTO.class);
        BoxDTO box = deliver.getBox();
        BookDTO book = deliver.getBook();
        System.out.println("BOX UUID: "+ box.getAddress());
        DeliveryDTO deliveryDTO=new DeliveryDTO(box, book);
        HttpEntity<String> deliverJsonNew = new HttpEntity<>(deliverJson, headers);
        System.out.println("---SENDING REQUEST TO BOXES-----");
        HttpEntity<DeliveryDTO> deliveryToDo = new HttpEntity<>(deliveryDTO);
        ResponseEntity<Void> response1 = restTemplate
                .exchange(URL + "/api/boxes/deliver", HttpMethod.POST, deliveryToDo, Void.class);

        Gson gson1 = new GsonBuilder().disableHtmlEscaping().create();
        DeliveryDTO deliverNew = gson1.fromJson(deliverJson, DeliveryDTO.class);
        SubmitBookorderDTO foundOrder = new SubmitBookorderDTO();
        System.out.println("SENDING REQUEST TO BOOKORDERS");
        ResponseEntity<BookorderDTO> response2 = restTemplate
                .exchange(URLO + "/api/bookorders", HttpMethod.GET, headersEntity, BookorderDTO.class);
        List<SubmitBookorderDTO> orders;
        orders =(Objects.requireNonNull(response2.getBody()).getBookorders());
        for(SubmitBookorderDTO o: orders){
            System.out.println("O IN FOR LOOP: "+o.toString());
            System.out.println("OBookId = "+o.getBook().getId().toString());
            if(o.getBook().getTitle().equals(book.getTitle())){
                System.out.println("O==BOOKNEW");
                foundOrder = o;
                break;
            }
        }
        if(foundOrder.isDeliveryState()){
            System.out.println("DELIVERY STATE=true");
            //delete order and next in order set
            HttpEntity<SubmitBookorderDTO> orderDelete = new HttpEntity<>(foundOrder);
            ResponseEntity<Void> response5 = restTemplate
                    .exchange(URLO + "/api/bookorders/delete", HttpMethod.DELETE,
                            orderDelete, Void.class);
        }
        else {
            System.out.println("DELIVERY STATE=false");

            foundOrder.setDeliveryState(true);
            HttpEntity<SubmitBookorderDTO> orderDelivered = new HttpEntity<>(foundOrder);
            System.out.println("SENDING PUT RESPONSE");
            ResponseEntity<Void> response5 = restTemplate
                    .exchange(URLO + "/api/bookorders/put", HttpMethod.PUT,
                            orderDelivered, Void.class);
            System.out.println("SENT RESPONSE");
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<Void> checkOut(@RequestBody String deliverJson) {
        //1018
        System.out.println("CHECKOUT FUNCTION");
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        DeliveryDTO deliver = gson.fromJson(deliverJson, DeliveryDTO.class);
        BoxDTO box = deliver.getBox();
        BookDTO book = deliver.getBook();
        System.out.println("BOX UUID: "+ box.getAddress());
        DeliveryDTO deliveryDTO=new DeliveryDTO(box, book);
        HttpEntity<String> deliverJsonNew = new HttpEntity<>(deliverJson, headers);
        System.out.println("---SENDING REQUEST TO BOXES-----");
        HttpEntity<DeliveryDTO> deliveryToDo = new HttpEntity<>(deliveryDTO);
        ResponseEntity<Void> response1 = restTemplate
                .exchange(URL + "/api/boxes/checkout", HttpMethod.POST, deliveryToDo, Void.class);

        return ResponseEntity.ok().build();
    }
}
