from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from database.connections import Base

class User(Base):
    __tablename__ = 'users'

    id = Column(Integer, primary_key=True, index=True)
    person_id = Column(Integer, ForeignKey('persons.id'))  # Relación con Person
    username = Column(String(100), unique=True, nullable=False)
    password = Column(String(255), nullable=False)

    person = relationship("Person", back_populates="users")  # Relación inversa
    audiometries = relationship("Audiometry", back_populates="user")  # Relación uno-a-muchos con Audiometry
