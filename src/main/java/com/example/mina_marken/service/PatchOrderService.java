package com.example.mina_marken.service;

import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.PatchOrder;

public interface PatchOrderService {

    PatchOrder getPatchOrderFromScoutIDAndPatch(String scoutID, Patch patch);
}
