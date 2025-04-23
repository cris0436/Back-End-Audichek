from sqlalchemy import Column, Integer
from database.Conections import Base

class Frecuency(Base):
    __tablename__ = 'frequencies'

    id = Column(Integer, primary_key=True, index=True)
    value = Column(Integer, nullable=False)
