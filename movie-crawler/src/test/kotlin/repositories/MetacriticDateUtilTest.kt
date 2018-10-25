package repositories

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MetacriticDateUtilTest{

    @Test
    fun `parse handles different cases`(){
        val case1 = "September 30, 2018"
        val case2 = "July 31, 2018"
        val date1 = MetacriticDateUtil.parseDate(case1)
        val date2 = MetacriticDateUtil.parseDate(case2)
        val case1Res = MetacriticDateUtil.formatDate(date1)
        val case2Res = MetacriticDateUtil.formatDate(date2)

        assertEquals(case1, case1Res)
        assertEquals(case2, case2Res)
    }
}