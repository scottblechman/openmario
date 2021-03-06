package com.scottblechman.openmario.model;

import com.badlogic.gdx.math.Vector2;

public class Player {

    private final float MASS = 4f;

    private final Vector2 position;
    private final Vector2 velocity;
    private final Vector2 acceleration;

    // External forces
    private final Vector2 gravityAcceleration;
    private boolean isOnSurface;    // Used to determine if gravity should apply

    public Player() {
        this.position = new Vector2(0,0);
        this.velocity = new Vector2(0,0);
        this.acceleration = new Vector2(0, 0);

        this.gravityAcceleration = new Vector2(0, -9.8f);
        this.isOnSurface = false;
    }

    public Player(Vector2 position, Vector2 velocity, Vector2 acceleration) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;

        this.gravityAcceleration = new Vector2(0, -9.8f);
        this.isOnSurface = false;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public float getMass() {
        return MASS;
    }

    public void setPosition(Vector2 position) {
        this.position.x = position.x;
        this.position.y = position.y;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.x = velocity.x;
        this.velocity.y = velocity.y;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration.x = acceleration.x;
        this.acceleration.y = acceleration.y;
    }

    public void addVelocity(float x, float y) {
        this.velocity.x += x;
        this.velocity.y += y;
    }

    public void addAcceleration(float x, float y) {
        this.acceleration.x += x;
        this.acceleration.y += y;
    }

    public void setOnSurface(boolean onSurface) {
        isOnSurface = onSurface;
    }

    public void update(float delta, float baseMovement) {
        velocity.x += acceleration.x;
        velocity.y += acceleration.y;

        velocity.y += gravityAcceleration.y * MASS;

        // Update velocity from external forces
        if(isOnSurface && velocity.y < 0) {
            velocity.y = 0;
        }

        // Update position from velocity
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        // Reset external forces
        isOnSurface = false;

        // Reset acceleration
        setAcceleration(new Vector2(0, 0));
    }
}
