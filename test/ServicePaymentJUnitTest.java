/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.br.data.Insert;
import com.br.data.Select;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runners.Parameterized;

/**
 *
 * @author Mahakit
 */
public class ServicePaymentJUnitTest {


    public String ordernum = "24100028";
    public String ordernumprv = "67100020";
    
    
     private int input;
    private int expected;

//    public ServicePaymentJUnitTest(int input, int expected) {
//        this.input = input;
//        this.expected = expected;
//    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, 2},
                {2, 4},
                {3, 6},
                {4, 6}
        });
    }

    @Test
    public void testMultiplyByTwo() {
        input = 3;
        expected = 4;
        assertEquals(expected, input * 2);
    }
    
    
 
       @Test
    public void twoPlusTwoShouldEqualFour() {
        TestingClass calculator = new TestingClass();
        assertEquals(4, calculator.add(2, 2));
    }

    @Test
    public void Checkcostcenter() throws Exception {
//        Select Select = new Select();
        TestingClass test = new TestingClass();
        assertTrue(test.CheckCostcenter("10", "101", "S102", 0));
//        assertSame()
    }

    @Test
    public void Checkcostcenterlowercase() throws Exception {
//        Select Select = new Select();
        TestingClass test = new TestingClass();
        assertFalse(test.CheckCostcenter("10", "101", "s102", 0));
//        assertSame()
    }

    @Test
    public void Checksupplier() throws Exception {
//        Select Select = new Select();
        TestingClass test = new TestingClass();
        assertFalse(test.Checksupplier("10", "101", "S102", 1));
//        assertSame()
    }

    @Test
    public void Checksupplierlowercase() throws Exception {
//        Select Select = new Select();
        TestingClass test = new TestingClass();
        assertFalse(test.Checksupplier("10", "101", "1020102225", 1));
//        assertSame()
    }

    @Test
    public void Checkifnull() throws Exception {
        Select Select = new Select();
        assertNotNull(Select.GetReferenceGRN_Bank("10", "101"));
    }

    @Test
    public void Checkifnull2() throws Exception {
        Select Select = new Select();
        assertNotNull(Select.GetReferenceGRN_Bank("400", "400"));
    }

    @Test
    public void checkcreationservices() throws Exception {
        TestingClass test = new TestingClass();
        ordernum = test.CheckServicesCreation("10", "101", "S102", "1020102225", 1);
        boolean testresult = false;
        if (ordernum.equals("")) {
            testresult = false;
        } else {
            testresult = true;
        }
        assertTrue(testresult);
    }

    @Test
    public void insertservices() throws Exception {
        TestingClass test = new TestingClass();
        boolean exist = test.insertcheck("10", "101", ordernum);
//        boolean testresult = false;
//        if(ordernum.equals("")){
//            testresult = false;
//        }
//        else{
//            testresult = true;
//        }
        assertTrue(exist);
    }

    @Test
    public void checkcreationpayment() throws Exception {
        TestingClass test = new TestingClass();
        ordernumprv = test.CheckPaymentCreation("10", "101", "S102", "1020102225", 1);
        boolean testresult = false;
        if (ordernumprv.equals("")) {
            testresult = false;
        } else {
            testresult = true;
        }
        assertTrue(testresult);
    }

    @Test
    public void insertpayment() throws Exception {
        TestingClass test = new TestingClass();
        boolean exist = test.insertcheckPRV("10", "101", ordernumprv, ordernum);
//        boolean testresult = false;
//        if(ordernum.equals("")){
//            testresult = false;
//        }
//        else{
//            testresult = true;
//        }
        assertTrue(exist);
    }
//    public void checkcreationgrn() throws Exception {
//        TestingClass test = new TestingClass();
//        ordernum = test.CheckServicesCreation("10", "101", "S102", "1020102225", 1);
//        boolean testresult = false;
//        if (ordernum.equals("")) {
//            testresult = false;
//        } else {
//            testresult = true;
//        }
//        assertTrue(testresult);
//    }

//    @Test
//    public void insertgrn() throws Exception {
//        TestingClass test = new TestingClass();
//        boolean exist = test.insertcheck("10", "101", ordernum);
////        boolean testresult = false;
////        if(ordernum.equals("")){
////            testresult = false;
////        }
////        else{
////            testresult = true;
////        }
//        assertTrue(exist);
//    }
    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Expected" + expected);
        System.out.println("Input" + input);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
