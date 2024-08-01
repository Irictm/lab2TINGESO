package fernando.iribarra.bonus_service.controllers;

import fernando.iribarra.bonus_service.entities.BonusEntity;
import fernando.iribarra.bonus_service.services.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bonus")
public class BonusController {
    @Autowired
    BonusService bonusService;

    @GetMapping("/{id}")
    public ResponseEntity<BonusEntity> getBonusById(@PathVariable Long id) {
        BonusEntity bonus = bonusService.getBonusById(id);
        return ResponseEntity.ok(bonus);
    }

    @GetMapping("/")
    public ResponseEntity<List<BonusEntity>> getBonusById() {
        List<BonusEntity> bonuses = bonusService.getAllBonus();
        return ResponseEntity.ok(bonuses);
    }

    @PostMapping("/")
    public ResponseEntity<BonusEntity> saveBonus(@RequestBody BonusEntity bonus) {
        BonusEntity newBonus = bonusService.saveBonus(bonus);
        return ResponseEntity.ok(newBonus);
    }

    @PutMapping("/")
    public ResponseEntity<BonusEntity> updateBonus(@RequestBody BonusEntity bonus) {
        BonusEntity updatedBonus = bonusService.updateBonus(bonus);
        return ResponseEntity.ok(updatedBonus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBonus(@PathVariable Long id) throws Exception {
        var isDeleted = bonusService.deleteBonus(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/consumeBonus/{brand}/{consume}")
    public ResponseEntity<Long> consumeBonus(@PathVariable String brand, @PathVariable boolean consume){
        Long value = bonusService.consumeBonus(brand, consume);
        return ResponseEntity.ok(value);
    }
}