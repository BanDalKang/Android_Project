package com.example.textrpggame

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Random
import kotlin.concurrent.thread

class CashShop private constructor() {
    private val bowPrice = 150
    private val staffPrice = 120

    companion object {
        @Volatile private var instance: CashShop? = null
        @Volatile private var lottoStatus: String? = null
        @Volatile private var isFinish: Boolean? = null

        fun getInstance(): CashShop {
            // 외부에서 요청왔을때 instance가 null인지 검증
            if(instance == null) {
                // synchronized로 외부 쓰레드의 접근을 막음
                synchronized(this) {
                    instance = CashShop()
                }
            }
            return instance!!
        }
    }

    fun purchaseWeapon(character:Character){
        if(character is Archer) {
            character?.run {
                if(money >= bowPrice) {
                    println("[구매 후 금액]: [${money} - ${bowPrice}] = ${money-bowPrice}")
                    money -= bowPrice
                    weapons.add("슈퍼 활")
                } else {
                    println("돈이 부족합니다.")
                }
            }
        } else if(character is Wizard) {
            character?.run {
                if(money >= staffPrice) {
                    println("[구매 후 금액]: [${money} - ${staffPrice}] = ${money-staffPrice}")
                    money -= staffPrice
                    weapons.add("슈퍼 스태프")
                } else {
                    println("돈이 부족합니다.")
                }
            }
        }
    }

    fun startLotto(character: Character, selectHorse: String) {
        var random = Random()
        val finalDst = 100
        isFinish = false
        thread(start = true) {
            var currentPosition = 0
            while(currentPosition < finalDst && isFinish == false) {
                currentPosition += (random.nextInt(5) + 1)

                println("1번말 현재 위치: ${currentPosition}m")
                runBlocking {
                    launch {
                        delay(300)
                    }
                }
            }
            if(lottoStatus == null || lottoStatus != "2번") {
                lottoStatus = "1번"
                isFinish = true
                println("1등: ${lottoStatus}말")

                if(lottoStatus.equals(selectHorse)) {
                    println("축하합니다! 당첨!")
                    println("상금으로 1만원 지급")

                    // 왜 이렇게밖에 작성했는지 이유를 생각하고
                    // 코드를 개선하기
                    if(character is Archer) {
                        character?.run {
                            money += 10000
                        }
                    } else if(character is Wizard) {
                        character?.run {
                            money += 10000
                        }
                    }
                }
            }
        }

        thread(start = true) {
            var currentPosition = 0
            while(currentPosition < finalDst && isFinish == false) {
                currentPosition += (random.nextInt(10) + 1)

                println("2번말 현재 위치: ${currentPosition}m")
                runBlocking {
                    launch {
                        delay(300)
                    }
                }
            }
            if(lottoStatus == null || lottoStatus != "1번") {
                lottoStatus = "2번"
                isFinish = true
                println("1등: ${lottoStatus}말")
                if(lottoStatus.equals(selectHorse)) {
                    println("축하합니다! 당첨!")
                    println("상금으로 1만원 지급")

                    // 왜 이렇게밖에 작성했는지 이유를 생각하고
                    // 코드를 개선하기
                    if(character is Archer) {
                        character?.run {
                            money += 10000
                        }
                    } else if(character is Wizard) {
                        character?.run {
                            money += 10000
                        }
                    }
                }
            }
        }
    }
}