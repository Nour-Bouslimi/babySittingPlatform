package org.example.babysitting.serviceImplement;

import org.example.babysitting.entities.Message;
import org.example.babysitting.entities.Reservation;
import org.example.babysitting.entities.User;
import org.example.babysitting.repository.MessageRepo;
import org.example.babysitting.repository.UserRepo;
import org.example.babysitting.service.MessageInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
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
            m.setSender(message.getSender());
            m.setReceiver(message.getReceiver());
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
    public List<Message> getMessagesByDate(LocalDate date) {
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
        msg.setDate(LocalDate.now());
        return messageRepo.save(msg);
    }

    @Override
    public List<Message> getConversation(Long userId1, Long userId2) {
        return messageRepo.getConversation(userId1, userId2);
    }
}
