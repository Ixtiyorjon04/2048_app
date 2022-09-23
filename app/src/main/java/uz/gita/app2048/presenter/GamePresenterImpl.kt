package uz.gita.app2048.presenter

import android.os.SystemClock
import android.util.Log


class GamePresenterImpl(private val view: GameContract.View, private val repository: GameContract.Repository) : GameContract.Presenter {
    override fun setScore(score: Int) {
        repository.setScore(score)
    }

    override fun restart() {

        repository.restart()
        view.changeState(repository.getMatrix())

    }


    override fun setList(list: ArrayList<Int>) {
        repository.setList(list)

    }

    override fun getScore(): Int {
        return repository.getScore()
    }

    override fun startGame() {
        view.changeState(repository.getMatrix())

    }

    override fun moveLeft() {
        repository.moveLeft()
        view.changeState(repository.getMatrix())
    }

    override fun moveRight() {
        repository.moveRight()
        view.changeState(repository.getMatrix())

    }

    override fun moveDown() {
        repository.moveDown()
        view.changeState(repository.getMatrix())
    }

    override fun moveUp() {
        repository.moveUp()
        view.changeState(repository.getMatrix())
    }

    override fun gameOver() {
        repository.submitA {
            Log.d("TT","Game Over")
            view.dialog()
        }
    }
}