import React, { useState } from "react";

const CardRegistration = () => {
  const [cardNumber, setCardNumber] = useState("");
  const [expiryDate, setExpiryDate] = useState("");
  const [cvv, setCvv] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Tarjeta registrada:", { cardNumber, expiryDate, cvv });
  };

  return (
    <div className="form-container">
      <h2>Registrar Tarjeta de Pago</h2>
      <form onSubmit={handleSubmit}>
        <label htmlFor="card_number">Número de tarjeta:</label>
        <input
          type="text"
          id="card_number"
          value={cardNumber}
          onChange={(e) => setCardNumber(e.target.value)}
          required
        />

        <label htmlFor="expiry_date">Fecha de vencimiento:</label>
        <input
          type="month"
          id="expiry_date"
          value={expiryDate}
          onChange={(e) => setExpiryDate(e.target.value)}
          required
        />

        <label htmlFor="cvv">CVV:</label>
        <input
          type="text"
          id="cvv"
          value={cvv}
          onChange={(e) => setCvv(e.target.value)}
          required
        />

        <button type="submit">Registrar tarjeta</button>
      </form>
    </div>
  );
}

export default CardRegistration;
