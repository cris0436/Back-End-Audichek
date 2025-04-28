from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from database.connections import Base

class Admin(Base):
    __tablename__ = 'admins'

    id = Column(Integer, ForeignKey('persons.id'), primary_key=True)  # Clave primaria de `Person`
    notes = Column(String(100), nullable=True)

    person = relationship("Person", back_populates="admin")  # Relación inversa con Person
