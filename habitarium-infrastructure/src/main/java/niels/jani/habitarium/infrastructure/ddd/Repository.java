package niels.jani.habitarium.infrastructure.ddd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface Repository<T extends AggregateRoot<ID>, ID extends Id> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    T findOneExisting(ID var1);

}
