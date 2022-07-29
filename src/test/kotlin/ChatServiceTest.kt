import org.junit.Test

import org.junit.Assert.*
import ru.netology.*

class ChatServiceTest {

    @Test
    fun addChat() {
        val user1Id = 1
        val user2Id = 2
        val chat10 = Chat(10, user1Id, user2Id)

        val chatService = ChatService
        chatService.addChat(chat10)

        assertNotEquals(0,chat10.id)
    }

    @Test
    fun shouldNotThrow() {

        val chatService = ChatService

        val message = Message(id = 1, text = "Сообщение для чата 10")
        val commentedPost = chatService.createMessage(1,message)

        assertNotEquals(null, commentedPost)

    }

    // 2 - Функция выкидывает исключение, если была попытка добавить сообщение к несуществующему чату.
    @Test(expected = ChatNotFoundError::class)
    fun shouldThrow() {
        val chatService = ChatService
        val message = Message(id = 1, text = "Сообщение для чата 15")
        chatService.createMessage(15,message)
    }



}