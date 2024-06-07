package fernando.iribarra.bonus_service.services;


import fernando.iribarra.bonus_service.entities.BonusEntity;
import fernando.iribarra.bonus_service.repositories.BonusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BonusService {
    @Autowired
    BonusRepository bonusRepository;

    public BonusEntity saveBonus(BonusEntity bonus) { return bonusRepository.save(bonus); }

    public BonusEntity getBonusById(Long id) { return bonusRepository.findById(id).get(); }

    public List<BonusEntity> getAllBonus() { return bonusRepository.findAll(); }

    public BonusEntity getBonusByBrand(String brand) { return bonusRepository.findByBrand(brand); }

    public BonusEntity updateBonus(BonusEntity bonus) { return bonusRepository.save(bonus); }

    public boolean deleteBonus(Long id) throws Exception {
        try {
            bonusRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public Long consumeBonus(String brand, boolean consume){
        long bonusDiscount = 0;
        BonusEntity bonus = getBonusByBrand(brand);
        if (bonus == null) {return 0L;}
        if (bonus.getNumberRemaining() > 0) {
            if (consume) {
                bonus.setNumberRemaining(bonus.getNumberRemaining()-1);
            }
            bonusDiscount = bonus.getAmount();
            updateBonus(bonus);
        }
        return bonusDiscount;
    }
}
