package com.noder.restapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.noder.restapi.models.Card;
import com.noder.restapi.repositories.CardRepository;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public Optional<Card> getCardById(Long id) {
        return Optional.of(cardRepository.findById(id).orElseThrow(() -> new RuntimeException("Card not found")));
    }

    public Card updateCard(Long id, Card updatedCard) throws RuntimeException {
        Card existingCard = cardRepository.findById(id).orElseThrow(() -> new RuntimeException("Card not found"));
        existingCard.setEncryptedCardNumber(updatedCard.getEncryptedCardNumber());
        existingCard.setCardHolderName(updatedCard.getCardHolderName());
        existingCard.setLast4Digits(updatedCard.getLast4Digits());
        return cardRepository.save(existingCard);
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }

}
