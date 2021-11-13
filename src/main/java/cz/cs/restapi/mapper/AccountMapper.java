package cz.cs.restapi.mapper;

import cz.cs.restapi.call.model.Account;
import cz.cs.restapi.dto.AccountDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
   // AccountDTO accountToDTO(Account account);
   // Account DTOtoAccount(AccountDTO accountDTO);
       List<AccountDTO> map(List<Account> accounts);
}
