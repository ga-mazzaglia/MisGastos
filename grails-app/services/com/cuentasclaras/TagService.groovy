package com.cuentasclaras

class TagService {

    def grailsApplication
    LoginService loginService

    Tag getTag(Long id) {
        User userLogged = loginService.getUserLogged()
        return Tag.findByIdAndUserAndEnabled(id, userLogged, true)
    }

    Tag[] getTags() {
        User userLogged = loginService.getUserLogged()
        return Tag.findAllByUserAndEnabled(userLogged, true, [sort: "position", order: "asc"])
    }

    Tag create(String detail, Integer position) {
        User userLogged = loginService.getUserLogged()
        Tag tag = null
        try {
            tag = new Tag()
            tag.detail = detail
            tag.user = userLogged
            tag.position = position
            tag.save(flush: true, failOnError: true)
        } catch (Exception ex) {
            println ex.toString()
        }
        return tag
    }

    Tag edit(Long id, String desc, Integer position, Boolean enabled) {
        User userLogged = loginService.getUserLogged()
        Tag tag = Tag.findByIdAndUser(id, userLogged)
        try {
            tag.detail = desc
            tag.position = position
            tag.enabled = enabled
            tag.save(flush: true, failOnError: true)
        } catch (Exception ex) {
            println ex.toString()
        }
        return tag
    }

    Tag changeStatus(Long id, Boolean enabled) {
        Tag tag = Tag.get(id)
        try {
            tag.enabled = enabled
            tag.save(flush: true, failOnError: true)
        } catch (Exception ex) {
            ex.printStackTrace()
            println ex.toString()
        }
        return tag
    }

    Integer getLastPosition() {
        User userLogged = loginService.getUserLogged()
        return Tag.findByUserAndEnabled(userLogged, true, [sort: "position", order: "desc"])?.position ?: 0
    }
}
