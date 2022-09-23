package uz.gita.app2048.repository

import android.util.Log



class GameRepositoryImpl : GameContract.Repository {

    private  var a :( () -> Unit)?=null
    private var score = 0
    private val MIN_VALUE = 2
    private val matrix: Array<Array<Int>> = arrayOf(
        arrayOf(0, 0, 0, 0), // 4, 8, 0, 0
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    init {
        addElement()
        addElement()
    }

    private fun gameover(): Boolean {
        for (i in 0 until matrix.size) {
            val chekList: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (chekList.isEmpty()) {
                    chekList.add(matrix[i][j])
                } else {
                    if (chekList.last() == matrix[i][j]){
                        return false
                    }else{
                        chekList.add(matrix[i][j])
                    }
                }
            }
        }
        for (i in 0 until matrix.size) {
            val chekList: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (chekList.isEmpty()) {
                    chekList.add(matrix[j][i])
                } else {
                    if (chekList.last() == matrix[j][i]){
                        return false
                    }else{
                        chekList.add(matrix[j][i])
                    }
                }
            }
        }
        return true

    }

    private fun addElement() {
        val elements = ArrayList<Pair<Int, Int>>()

        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == 0) elements.add(Pair(i, j))
            }
        }

        if (elements.size == 0) {
            if (gameover()) {
                a?.invoke()
                Log.d("T","Game Over")
            } else return
        }
        if (elements.size>0){
        elements.shuffle()

        val element = elements[0]
        matrix[element.first][element.second] = MIN_VALUE
    }}

    override fun getMatrix(): Array<Array<Int>> = matrix

    override fun moveLeft() {
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == 0) continue;

                if (amounts.isEmpty()) {
                    amounts.add(matrix[i][j])
                } else {
                    if (amounts.last() == matrix[i][j] && state) {
                        state = false
                        amounts[amounts.lastIndex] = matrix[i][j] * 2
                        score += matrix[i][j] * 2
                    } else {
                        state = true
                        amounts.add(matrix[i][j])
                    }
                }
                matrix[i][j] = 0
            }

            for (j in 0 until amounts.size) {
                matrix[i][j] = amounts[j]
            }
        }
        addElement()

    }

    override fun moveRight() {

        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (matrix[3 - i][3 - j] == 0) continue;

                if (amounts.isEmpty()) {
                    amounts.add(matrix[3 - i][3 - j])
                } else {
                    if (amounts.last() == matrix[3 - i][3 - j] && state) {
                        state = false
                        amounts[amounts.lastIndex] = matrix[3 - i][3 - j] * 2
                        score += matrix[3 - i][3 - j] * 2
                    } else {
                        state = true
                        amounts.add(matrix[3 - i][3 - j])
                    }
                }
                matrix[3 - i][3 - j] = 0
            }

            for (j in 0 until amounts.size) {
                matrix[3 - i][3 - j] = amounts[j]
            }
        }
        addElement()

    }

    override fun moveDown() {
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (matrix[3 - j][3 - i] == 0) continue;

                if (amounts.isEmpty()) {
                    amounts.add(matrix[3 - j][3 - i])
                } else {
                    if (amounts.last() == matrix[3 - j][3 - i] && state) {
                        state = false
                        amounts[amounts.lastIndex] = matrix[3 - j][3 - i] * 2
                        score += matrix[3 - j][3 - i] * 2
                    } else {
                        state = true
                        amounts.add(matrix[3 - j][3 - i])
                    }
                }
                matrix[3 - j][3 - i] = 0
            }

            for (j in 0 until amounts.size) {
                matrix[3 - j][3 - i] = amounts[j]
            }
        }
        addElement()
    }

    override fun moveUp() {
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (matrix[j][i] == 0) continue;

                if (amounts.isEmpty()) {
                    amounts.add(matrix[j][i])
                } else {
                    if (amounts.last() == matrix[j][i] && state) {
                        state = false
                        amounts[amounts.lastIndex] = matrix[j][i] * 2
                        score += matrix[j][i] * 2

                    } else {
                        state = true
                        amounts.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }

            for (j in 0 until amounts.size) {
                matrix[j][i] = amounts[j]
            }
        }
        addElement()
    }

    override fun getScore(): Int {
        return score
    }

    override fun setScore(score: Int) {
        this.score = score
    }

    override fun setList(list: ArrayList<Int>) {
        var count = 0
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[i].size) {
                matrix[i][j] = list[count]
                count++
            }
        }
    }

    override fun restart() {
        addElement()
        addElement()
    }


    override fun submitA(block: () -> Unit) {
        a = block;
    }
}