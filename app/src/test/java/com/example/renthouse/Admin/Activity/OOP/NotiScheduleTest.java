package com.example.renthouse.Admin.Activity.OOP;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.renthouse.Admin.OOP.NotiSchedule;

public class NotiScheduleTest {

    @Test
    public void testConstructorAndGettersSetters() {
        String key = "testKey";
        String title = "Test Title";
        String content = "Test Content";
        String receiver = "Test Receiver";
        String date = "2023-11-10";
        String time = "12:00 PM";
        String loop = "daily";

        NotiSchedule notiSchedule = new NotiSchedule(key, title, content, receiver, date, time, loop);

        assertEquals(key, notiSchedule.getKey());
        assertEquals(title, notiSchedule.getTitle());
        assertEquals(content, notiSchedule.getContent());
        assertEquals(receiver, notiSchedule.getReceiver());
        assertEquals(date, notiSchedule.getDate());
        assertEquals(time, notiSchedule.getTime());
        assertEquals(loop, notiSchedule.getLoop());

        String newKey = "newTestKey";
        notiSchedule.setKey(newKey);
        assertEquals(newKey, notiSchedule.getKey());

        String newTitle = "New Test Title";
        notiSchedule.setTitle(newTitle);
        assertEquals(newTitle, notiSchedule.getTitle());

        String newContent = "New Test Content";
        notiSchedule.setContent(newContent);
        assertEquals(newContent, notiSchedule.getContent());

        String newReceiver = "New Test Receiver";
        notiSchedule.setReceiver(newReceiver);
        assertEquals(newReceiver, notiSchedule.getReceiver());

        String newDate = "2023-11-11";
        notiSchedule.setDate(newDate);
        assertEquals(newDate, notiSchedule.getDate());

        String newTime = "1:00 PM";
        notiSchedule.setTime(newTime);
        assertEquals(newTime, notiSchedule.getTime());

        String newLoop = "weekly";
        notiSchedule.setLoop(newLoop);
        assertEquals(newLoop, notiSchedule.getLoop());
    }

    @Test
    public void testEmptyConstructor() {
        NotiSchedule notiSchedule = new NotiSchedule();

        assertNotNull(notiSchedule);
    }
}
