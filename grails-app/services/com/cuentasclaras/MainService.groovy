package com.cuentasclaras

class MainService {

    def User[] getFriends(Long userId) {
        return User.get(userId).friends;
    }

    def MovementType[] getMovementTypes() {
        return MovementType.findAll();
    }

    def Tag[] getTags(Long userId) {
        User user = User.get(userId);
        return Tag.findAllByUser(user);
    }

}
