from fastapi import FastAPI, Depends
from sqlalchemy.orm import Session
from database.Conections import SessionLocal
from models import Usuario

class DataBaseSession(): # Asegúrate de importar el modelo correctamente
    
    def get_db(self):
        db = SessionLocal()
        try:
            yield self.db
        finally:
           self.db.close()