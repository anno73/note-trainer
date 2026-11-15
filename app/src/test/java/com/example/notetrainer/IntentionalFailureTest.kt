package com.example.notetrainer

import org.junit.Assert.fail
import org.junit.Test

class IntentionalFailureTest {
    @Test
    fun workflowMustNotPublishWhenTestsFail() {
        fail("Intentional CI failure to verify artifact publication is blocked")
    }
}
