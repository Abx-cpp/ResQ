package com.example.resq_1.resource;

import com.example.resq_1.resource.resource;
import com.example.resq_1.resource.resourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class resourceController {

    private final resourceService resourceService;

    @Autowired
    public resourceController(resourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public List<resource> getAllResources() {
        return resourceService.getAllResources();
    }

    @GetMapping("/{id}")
    public ResponseEntity<resource> getResourceById(@PathVariable Integer id) {
        return resourceService.getResourceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public resource createResource(@RequestBody resource resource) {
        return resourceService.saveResource(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Integer id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
}
