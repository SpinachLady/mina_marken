package com.example.mina_marken.service;

import com.example.mina_marken.model.entity.ScoutGroup;

import java.util.List;

public interface ScoutGroupService {

    List<ScoutGroup> getAllScoutGroups();

    ScoutGroup getScoutGroupFromName(String name);
    ScoutGroup getScoutGroupFromID(Long id);
}
