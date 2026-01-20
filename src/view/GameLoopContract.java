package view;

import java.awt.event.KeyListener;

public interface GameLoopContract {
    void refreshView();

    void addKeyListener (KeyListener key);
}