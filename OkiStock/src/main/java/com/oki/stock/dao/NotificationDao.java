package com.oki.stock.dao;

import com.oki.stock.entity.Notification;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDao {

    Notification queryLastNotification();
}
