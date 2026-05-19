from fastapi import FastAPI, Depends, HTTPException
from sqlalchemy.orm import Session
from typing import List
import models, schemas, auth, database
from database import engine
models.Base.metadata.create_all(bind=engine)
app = FastAPI(title="RAKSHA AI")
@app.post("/signup", response_model=schemas.User)
def signup(user: schemas.UserCreate, db: Session = Depends(database.get_db)):
    hashed = auth.get_password_hash(user.password)
    db_user = models.User(email=user.email, full_name=user.full_name, phone_number=user.phone_number, hashed_password=hashed)
    db.add(db_user); db.commit(); db.refresh(db_user)
    return db_user
@app.post("/login", response_model=schemas.Token)
def login(user: schemas.UserCreate, db: Session = Depends(database.get_db)):
    db_user = db.query(models.User).filter(models.User.email == user.email).first()
    if not db_user or not auth.verify_password(user.password, db_user.hashed_password): raise HTTPException(status_code=401)
    return {"access_token": auth.create_access_token({"sub": db_user.email}), "token_type": "bearer"}
@app.post("/trigger-sos", response_model=schemas.EmergencyAlert)
def trigger(alert: schemas.EmergencyAlertCreate, current_user: models.User = Depends(auth.get_current_user), db: Session = Depends(database.get_db)):
    new_alert = models.EmergencyAlert(user_id=current_user.id, latitude=alert.latitude, longitude=alert.longitude)
    db.add(new_alert); db.commit(); db.refresh(new_alert)
    return new_alert
@app.get("/all-alerts", response_model=List[schemas.EmergencyAlert])
def get_alerts(db: Session = Depends(database.get_db)):
    return db.query(models.EmergencyAlert).all()
