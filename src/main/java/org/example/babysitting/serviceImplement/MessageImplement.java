package org.example.babysitting.serviceImplement;

import org.example.babysitting.DTO.ConversationDTO;
import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Reservation;
import org.example.babysitting.entities.User;
import org.example.babysitting.repository.MessageRepo;
import org.example.babysitting.repository.UserRepo;
import org.example.babysitting.service.MessageInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MessageImplement implements MessageInterface {

    @Autowired
    MessageRepo messageRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public Message addMessage(Message message) {
        return messageRepo.save(message);
    }

    @Override
    public void deleteMessage(Long id) {
        if(!messageRepo.existsByIdMsg(id)) {
            throw new RuntimeException("Message with id " + id + " does not exist");
        }
        messageRepo.deleteById(id);

    }

    @Override
    public List<Message> addListMessages(List<Message> messages) {
        return messageRepo.saveAll(messages);
    }

    @Override
    public Message updateMessage(Long id, Message message) {



        Message m = getMessageById(id);
        if (m != null) {
            m.setContent(message.getContent());
            m.setDate(message.getDate());
          //  m.setSender(message.getSenderId());
           // m.setReceiver(message.getReceiver());
            // Récupérer sender User à partir de l'id envoyé
            if (message.getSenderId() != null) {
                Optional<User> senderOpt = userRepo.findById(message.getSenderId());
                senderOpt.ifPresent(m::setSender);
            }

            // Pour receiver, pareil si c'est un User ou un id à gérer pareil
            if (message.getReceiverId() != null) {
                Optional<User> receiverOpt = userRepo.findById(message.getReceiverId());
                receiverOpt.ifPresent(m::setReceiver);
            }
            return messageRepo.save(m);
        } else {
            return null;
        }
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    @Override
    public Message getMessageById(Long id) {
        return messageRepo.findById(id).orElse(null);
    }

    /*@Override
    public List<Message> getMessagesByIdUSer(Long idUser) {
        return messageRepo.findByIdUser(idUser);
    }*/

    @Override
    public List<Message> getMessagesByDate(LocalDateTime date) {
        return messageRepo.findByDate(date);
    }

    @Override
    public Message sendMessage(Long senderId, Long receiverId, String content) {
        User sender = userRepo.findById(senderId).orElse(null);
        User receiver = userRepo.findById(receiverId).orElse(null);

        Message msg = new Message();
        msg.setSender(sender);
        msg.setReceiver(receiver);
        msg.setContent(content);
        msg.setDate(LocalDateTime.now());
        return messageRepo.save(msg);
    }

    @Override
    public List<Message> getConversation(Long userId1, Long userId2) {
        return messageRepo.getConversation(userId1, userId2);
    }

    @Override
    public List<ConversationDTO> getAllConversations(Long userId) {
        List<Object[]> raw = messageRepo.findAllConversationsRaw(userId);
        Map<Long, ConversationDTO> map = new LinkedHashMap<>();

        for (Object[] row : raw) {
            Long otherUserId = ((Number) row[0]).longValue();       // otherUserId
            String lastMessage = (String) row[1];                   // lastMessage
            LocalDateTime date = ((Timestamp) row[2]).toLocalDateTime(); // date
            String firstName = (String) row[3];                      // firstName
            String lastName = (String) row[4];                       // lastName
            String photo = (String) row[5];                          // photo

            map.put(otherUserId, new ConversationDTO(otherUserId, lastMessage, date, firstName, lastName, photo));
        }
        return new ArrayList<>(map.values());
    }


}
