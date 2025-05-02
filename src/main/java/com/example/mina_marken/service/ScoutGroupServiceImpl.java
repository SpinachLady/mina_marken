package com.example.mina_marken.service;

import com.example.mina_marken.model.entity.ScoutGroup;
import com.example.mina_marken.repo.ScoutGroupRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoutGroupServiceImpl implements ScoutGroupService{

    private final ScoutGroupRepo scoutGroupRepo;

    public ScoutGroupServiceImpl(ScoutGroupRepo scoutGroupRepo) {
        this.scoutGroupRepo = scoutGroupRepo;
    }

    public List<ScoutGroup> getAllScoutGroups() {
        return scoutGroupRepo.findAll();
    }

    public ScoutGroup getScoutGroupFromName(String name) {
        return scoutGroupRepo.findByName(name);
    }

    public ScoutGroup getScoutGroupFromID(Long id) {
        return scoutGroupRepo.findById(id).orElse(null);
    }

}
