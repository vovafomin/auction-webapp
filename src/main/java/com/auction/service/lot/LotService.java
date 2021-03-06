package com.auction.service.lot;

import com.auction.dto.lot.LotDto;
import com.auction.entity.lot.Lot;
import com.auction.exception.LotNotFoundException;
import com.auction.mapper.lot.LotMapper;
import com.auction.repository.lot.LotRepository;
import com.auction.validation.lot.LotValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LotService implements LotServiceProvider {

    private final LotRepository lotRepository;
    private final LotValidator lotValidator;

    @Override
    public LotDto createOne(LotDto lotDto) {
        lotValidator.validateCreate(lotDto);
        Lot lot = LotMapper.instance.convert(lotDto);
        return LotMapper.instance.convert(lotRepository.save(lot));
    }

    public List<LotDto> getAllLots() {
        return LotMapper.instance.convert(lotRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteLot(Long lotId) {
        lotRepository.findById(lotId)
                     .orElseThrow(() -> new LotNotFoundException(lotId));
        lotRepository.deleteById(lotId);
    }

    @Override
    public LotDto getLot(Long lotId) {
        Lot retrievedLot =
            lotRepository.findById(lotId).orElseThrow(() -> new LotNotFoundException(lotId));
        return LotMapper.instance.convert(retrievedLot);
    }
}
