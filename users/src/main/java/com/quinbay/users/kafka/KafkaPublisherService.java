//package com.quinbay.users.kafka;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.quinbay.users.model.Users;
//import com.quinbay.users.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.json.JSONArray;
//
//import java.lang.reflect.Type;
//import java.util.List;
//
//public class KafkaPublisherService {
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    KafkaTemplate kafkaTemplate;
//
//    @Autowired
//    UserRepository userRepository;
//
//    public void sendWholesalerForBilling(Finance finance){
//        try{
//            List<Users> users = userRepository.findAll();
//            JSONArray jsArray = new JSONArray(users);
//            Users[] userDetails = objectMapper.readValue(jsArray, Users[].class);
//            kafkaTemplate.send("send.finance", this.objectMapper.);
//            System.out.println("\n\t sent kafka message!!!!!!!!!!!!!!!");
//        }catch(Exception e){
//            System.out.println("error is building "+ e);
//        }
//    }
//}
