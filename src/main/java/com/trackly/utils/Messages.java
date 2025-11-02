package com.trackly.utils;

import java.util.Map;

import com.trackly.enums.RespCode;

public class Messages {
  public static final Map<RespCode, String> RegisterMessages = Map.of(
    RespCode.OK, "L'utente è stato creato con successo!",
    RespCode.BAD_REQUEST, "Qualcosa manca! Controlla",
    RespCode.UNAUTHORIZED, "Autenticazione fallita",
    RespCode.NOT_FOUND, "Qualcosa è andato storto!",
    RespCode.CONFLICT, "Esiste già un utente con questa email!",
    RespCode.INTERNAL_SERVER_ERROR, "Errore interno del server"
  );

  public static final Map<RespCode, String> LoginMessages = Map.of(
    RespCode.OK, "Login eseguito con successo!",
    RespCode.NOT_FOUND, "Utente non trovato! Esegui una registrazione!",
    RespCode.BAD_REQUEST, "Credenziali errate!",
    RespCode.UNAUTHORIZED, "Password non valida!"
  );
}
