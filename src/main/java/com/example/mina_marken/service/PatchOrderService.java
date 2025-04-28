package com.example.mina_marken.service;

import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.PatchOrder;
import com.example.mina_marken.model.entity.ScoutGroup;

import java.util.List;

public interface PatchOrderService {

    PatchOrder getPatchOrderFromScoutIDAndPatch(String scoutID, Patch patch);
    List<PatchOrder> getPatchOrderFromGroupIDAndPatch(ScoutGroup scoutGroup, Patch patch);
    String getInfoTextFromPatchOrders(List<PatchOrder> patchOrders);
    void savePatchOrder(PatchOrder patchOrder);
    List<PatchOrder> getAllPatchOrders();

}
