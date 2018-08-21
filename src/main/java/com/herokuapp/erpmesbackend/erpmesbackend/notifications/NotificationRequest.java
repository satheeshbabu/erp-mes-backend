package com.herokuapp.erpmesbackend.erpmesbackend.notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NonNull
    private String instruction;

    private String description;
    private Long orderId;
    private Long notifierId;
    private Long transfereeId;

    @NonNull
    private List<Long> consigneeIds;
}