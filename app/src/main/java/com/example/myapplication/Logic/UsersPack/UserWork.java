package com.example.myapplication.Logic.UsersPack;

import java.util.ArrayList;

public class UserWork {

    private ArrayList<User> users = new ArrayList<>();

    /**
     * =add
     * @return
     */
    private void register(User newUser){
        users.add(newUser);
    }

    /**
     * Функция проверки существования введенной почты
     * @param mail - почта
     * @return true - такая почта есть в БД приложения
     * @return false - такой почты нет в БД приложения
     */
    private boolean isRegistered(String mail){
        for (User el:
                users) {
            if (el.getMail().equals(mail))
                return true;
        }
        return false;
    }

    /**
     * функция авторизации пользователя
     * @param mail - почта
     * @param password - пароль
     * @return true - пароль соответствует почте
     * false - пароль НЕ соответствует почте
     */
    private boolean authorize(String mail, String password){
        for (User el:
             users) {
            if (el.getMail().equals(mail) && el.getPassword().equals(password))
                return true;
        }
        return false;
    }

    /**
     * Функция редактирования данных пользователя
     * @param id - id для изменения
     * @param changedUser - измененные данные пользователя
     */
    private void change(int id, User changedUser){
        users.add(id, changedUser);
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
