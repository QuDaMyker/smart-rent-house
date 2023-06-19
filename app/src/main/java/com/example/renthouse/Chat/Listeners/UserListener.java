package com.example.renthouse.Chat.Listeners;

import com.google.firebase.firestore.auth.User;

public interface UserListener {
    void onUserClicked(User user);
}
