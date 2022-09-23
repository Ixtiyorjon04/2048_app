


interface GameContract {

    interface Repository {
        fun getMatrix(): Array<Array<Int>>
        fun moveLeft()
        fun moveRight()
        fun moveDown()
        fun moveUp()
        fun getScore(): Int
        fun setScore(score: Int)
        fun setList(list: ArrayList<Int>)
        fun restart()



        fun submitA(a: () -> Unit)

    }

    interface View {
        fun dialog()
        fun changeState(matrix: Array<Array<Int>>)
        fun restart()
    }

    interface Presenter {
        fun setScore(score: Int)
        fun restart()
        fun setList(list: ArrayList<Int>)
        fun getScore(): Int
        fun startGame()
        fun moveLeft()
        fun moveRight()
        fun moveDown()
        fun moveUp()
        fun gameOver()
    }
}