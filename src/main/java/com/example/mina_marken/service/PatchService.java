package com.example.mina_marken.service;

import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.ScoutGroup;

import java.util.List;

public interface PatchService {
    List<Patch> getPatchesFromIdCode(String idCode);
    Patch getPatchFromPatchId (Long id);
    Patch getPatchFromName(String name);
    List<Patch> getPatchesFromCurrentScoutGroup(ScoutGroup scoutGroup);
    List<Patch> getAllPatches();

}
