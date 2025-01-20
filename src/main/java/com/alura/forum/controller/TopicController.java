package com.alura.forum.controller;

import com.alura.forum.model.Topic;
import com.alura.forum.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        Optional<Topic> topic = topicService.findById(id);
        return topic.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Topic createTopic(@RequestBody Topic topic) {
        return topicService.save(topic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody Topic topicDetails) {
        Optional<Topic> topic = topicService.findById(id);
        if (topic.isPresent()) {
            Topic updatedTopic = topic.get();
            updatedTopic.setTitle(topicDetails.getTitle());
            updatedTopic.setMessage(topicDetails.getMessage());
            updatedTopic.setCreationDate(topicDetails.getCreationDate());
            updatedTopic.setStatus(topicDetails.getStatus());
            updatedTopic.setAuthor(topicDetails.getAuthor());
            updatedTopic.setCourse(topicDetails.getCourse());
            topicService.save(updatedTopic);
            return ResponseEntity.ok(updatedTopic);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}