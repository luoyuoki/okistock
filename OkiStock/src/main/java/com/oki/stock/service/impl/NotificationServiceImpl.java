package com.oki.stock.service.impl;

import com.oki.stock.dao.NotificationDao;
import com.oki.stock.entity.Notification;
import com.oki.stock.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    @Override
    public Notification getLastNotification() {
        return notificationDao.queryLastNotification();
    }
}
