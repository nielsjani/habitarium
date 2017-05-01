package niels.jani.habitarium.infrastructure.ddd;

import niels.jani.habitarium.infrastructure.messaging.HabitariumEventBus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RepositoryFactoryBean<R extends JpaRepository<T,ID>, T extends AggregateRoot<ID>, ID extends Id> extends JpaRepositoryFactoryBean<R, T, ID> {

    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private HabitariumEventBus eventBus;

    @Override
    protected RepositoryFactorySupport doCreateRepositoryFactory() {
        return new RepositoryFactory(entityManager, eventBus);
    }

    public static class RepositoryFactory<T extends AggregateRoot<ID>, ID extends Id> extends JpaRepositoryFactory {

        private EntityManager entityManager;
        private HabitariumEventBus eventBus;

        public RepositoryFactory(EntityManager entityManager, HabitariumEventBus eventBus) {
            super(entityManager);
            this.entityManager = entityManager;
            this.eventBus = eventBus;
        }

        @Override
        protected Object getTargetRepository(RepositoryInformation information) {
            if (AggregateRoot.class.isAssignableFrom(information.getDomainType())) {
                return new RepositoryImpl<>((Class<T>) information.getDomainType(), entityManager, eventBus);
            }
            return super.getTargetRepository(information);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            if (AggregateRoot.class.isAssignableFrom(metadata.getDomainType())) {
                return RepositoryImpl.class;
            }
            return super.getRepositoryBaseClass(metadata);
        }
    }

}
