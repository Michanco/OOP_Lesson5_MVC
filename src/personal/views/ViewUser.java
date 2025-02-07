package personal.views;

import personal.controllers.UserController;
import personal.model.User;
import personal.views.validator.Namesvalidator;

import java.util.List;
import java.util.Scanner;

public class ViewUser {

    private UserController userController;

    public ViewUser(UserController userController) {
        this.userController = userController;
    }

    public void run(){
        Commands com = Commands.NONE;

        while (true) {
            String command = prompt("Введите команду: ");
            try {
                com = Commands.valueOf(command.toUpperCase().trim());
                if (com == Commands.EXIT) return;
                switch (com) {
                    case CREATE:
                        createUser();
                        break;
                    case READ:
                        readUser();
                        break;
                    case LIST:
                        readList();
                        break;
                    case UPDATE:
                        updateUser();
                        break;
                    case DELETE:
                        deleteUser();
                        break;
                }
            }catch (Exception e){
                System.out.println(e.getMessage());;
            }
        }
    }

    private void deleteUser() throws Exception {
        User user = getUser();
        userController.deleteUser(user);
    }

    private void updateUser() throws Exception{
        User user = getUser();
        User updatedUser = newUser();
        updatedUser.setId(user.getId());
        User savedUser = userController.updateUser(updatedUser);
        System.out.println(savedUser);
    }

    private void readList() {
        List<User> listUsers = userController.readAllUsers();
        for (User user:listUsers) {
            System.out.println(user);
        }
    }

    private void readUser() throws Exception {
        User user = getUser();
        System.out.println(user);
    }

    private User getUser() throws Exception {
        String id = prompt("Идентификатор пользователя: ");
        User user = userController.readUser(id);
        System.out.println(user);
        return user;
    }

    private void createUser() throws Exception {
        User user = newUser();
        userController.saveUser(user);
    }

    private User newUser() throws Exception {
        String firstName = prompt("Имя: ");
        new Namesvalidator(firstName).validate();
        String lastName = prompt("Фамилия: ");
        new Namesvalidator(lastName).validate();
        String phone = prompt("Номер телефона: ");
        User user = new User(firstName, lastName, phone);
        return user;
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }
}
