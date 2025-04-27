from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from database.connections import Base

class Audiometry(Base):
    __tablename__ = 'audiometries'

    id = Column(Integer, primary_key=True, index=True)
    patient_id = Column(Integer, ForeignKey('users.id'))  # Relación con User (paciente)
    
    user = relationship("User", back_populates="audiometries")  # Relación inversa en el modelo User
    audiometry_results = relationship("AudiometryResults", back_populates="audiometry")
