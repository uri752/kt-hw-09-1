package ru.netology

// чат-переписка между двумя пользователями user1 и user2
data class Chat(val id: Int, val user1Id: Int = 0, val user2Id: Int = 0, val messages: MutableList<Message> = mutableListOf()) {

}

data class Message(val id: Int, val userId: Int = 0, val text: String = "", var deleted: Boolean = false, var unreaded: Boolean = true) {

}

object ChatService {

    private val chats = mutableListOf<Chat>()
    // CRUD-операции (Create, Read, Update, Delete)

   // *** Операции с Чатами
    fun addChat(chat: Chat): Chat {
        chats += chat
        return chats.last()
    }

    fun getChatById(chatId: Int): Chat? {
        for(chat in chats) {
            if(chat.id == chatId) {
                return chat
            }
        }
        return null
    }

    // получить чаты пользователя
    fun getChatByUserId(userId: Int): Chat? {
        for(chat in chats) {
            if( (chat.user1Id == userId) || (chat.user2Id == userId) )  {
                return chat
            }
        }
        return null
    }

    fun updateChat(newChat: Chat):Boolean {
        for((index, chat) in chats.withIndex()) {
            if(chat.id == newChat.id) {
                chats[index] = newChat;
                return true
            }
        }
        return false
    }

    fun deleteChat(chat: Chat) {
        chats.remove(chat)
    }

   // *** Операции с Сообщениями в Чатах
   fun createMessage(chatId: Int, message: Message) {
       chats.find {it.id == chatId}?.messages?.add(message)
   }

   fun deleteMessage(chatId: Int, messageId: Int) {
        chats.find{it.id == chatId}?.messages?.find{it.id == messageId}?.deleted = true
   }

   fun restoreMessage(chatId: Int, messageId: Int) {
       chats.find{it.id == chatId}?.messages?.find{it.id == messageId}?.deleted = false
   }

    // *** Дополнительная функциональность
    // 1 - Получить количество непрочитанных чатов
    fun getUnreadChatCount(): Int {
        var countChats = 0;
        for(chat in chats) {
            for(message in chat.messages) {
                if(message.unreaded) {
                    countChats ++
                    break
                }
            }
        }
        return countChats
    }

    // 2 - Получить список чатов, где есть последнее сообщение, иначе вернуть "нет сообщений"
    fun getChats(userId: Int): MutableList<String>  {
        var listChats: MutableList<String> = mutableListOf()
        for(chat in chats) {
            if(chat.user1Id == userId || chat.user2Id == userId) {
                var messageText: String = "нет сообщений"
                if(chat.messages.size != 0) {
                    messageText = chat.messages.last().text
                }
                listChats.add(messageText)
            }
        }
        return listChats
    }

    // 3 - Получить список сообщений чата
    fun getListMessage(chatId: Int, lastMessageId: Int, countMessage: Int): MutableList<Message> {
        val listMessage = mutableListOf<Message>()

        for(chat in chats){
            if(chat.id == chatId) {
                var count: Int = 0
                for(message in chat.messages) {
                    if(message.id >= lastMessageId) {
                        message.unreaded = false
                        listMessage.add(message)
                        count ++
                        if(count > countMessage) break
                    }
                }
            }
        }

        return listMessage
    }
}

fun main() {
    val chatService = ChatService

    val user1Id = 1
    val user2Id = 2

    val chat1 = Chat(1, user1Id, user2Id)
    chatService.addChat(chat1)

    chatService.createMessage(1,Message(1,user1Id,"Привет"))
    chatService.createMessage(1,Message(2,user2Id,"Привет"))

    chatService.createMessage(1,Message(3,user1Id,"Как дела?"))
    chatService.createMessage(1,Message(4,user2Id,"Нормально, сам как?"))

    chatService.createMessage(1,Message(5,user1Id,"Тоже хорошо, спасибо"))

    val chat2 = Chat(2, user1Id, user2Id)
    chatService.addChat(chat2)

    // 1 - Получить информацию о количестве непрочитанных чатов
    println("Количество непрочитанных чатов: ${chatService.getUnreadChatCount()}")

    // 2 - Получить список чатов
    println("Список чатов: ${chatService.getChats(1)}")

    // 3 - Получить список сообщений чата
    println("Список сообщений чата: ${chatService.getListMessage(1, 2, 3)}")

}

