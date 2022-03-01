package org.example;

import static org.junit.Assert.assertTrue;

import org.example.dao.AccountRepository;
import org.example.dao.InMemAccountDao;
import org.example.dao.Repository;
import org.example.dto.CreateAccountDTO;
import org.example.services.AccountService;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.misusing.MissingMethodInvocationException;

import java.util.MissingFormatArgumentException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    private AccountRepository<Integer, Double> accountRepository = new InMemAccountDao();

    private AccountService service = new AccountService(accountRepository);
    private CreateAccountDTO createAccountDTO = new CreateAccountDTO(1000000000, 0.0);


    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void createAcountInRepo(){
        accountRepository.createAccount(createAccountDTO.getAccountNumber(), createAccountDTO.getAccountBalance());
    }

    @Test
    public void listAllAccountsServiceTest(){
        service.listAllAccounts();
    }

    @Test(expected = RuntimeException.class)
    public void createNullAccountInRepo(){
        accountRepository.createAccount(null, null);
    }

    @Test(expected = Exception.class)
    public void accountReturnsBalance(){
        Mockito.when(accountRepository.getAccountNumber()).thenReturn(null);
    };

    @Test(expected = Exception.class)
    public void getAccountNumberDTO(){
        Mockito.when(createAccountDTO.getAccountNumber()).thenReturn(null);
    }
}
