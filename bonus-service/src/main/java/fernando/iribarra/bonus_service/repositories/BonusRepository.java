package fernando.iribarra.bonus_service.repositories;

import fernando.iribarra.bonus_service.entities.BonusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonusRepository extends JpaRepository<BonusEntity, Long> {
    public BonusEntity findByBrand(String brand);
}