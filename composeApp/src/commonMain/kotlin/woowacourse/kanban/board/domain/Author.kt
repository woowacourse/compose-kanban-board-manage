package woowacourse.kanban.board.domain

sealed class Author {
    data object NONE : Author()
    data class User(val name: String) : Author()
}

object AuthorPolicy {
    fun selectableAuthors(isRequired: Boolean): List<Author> {
        val authors = listOf(Author.User("다이노"), Author.User("페임스"))
        return if (isRequired) authors else listOf(Author.NONE) + authors
    }
}
