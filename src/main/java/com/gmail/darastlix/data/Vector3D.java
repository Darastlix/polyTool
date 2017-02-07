package com.gmail.darastlix.data;

import org.bukkit.Location;

public class Vector3D
{
    public float x,y,z;

    public Vector3D()
    {
        x = y = z = 0;
    }

    public Vector3D(float x)
    {
        this.x = x;
        y = x;
        z = x;
    }

    public Vector3D(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(Location location)
    {
        x = location.getBlockX();
        y = location.getBlockY();
        z = location.getBlockZ();
    }

    public float getCoords(int coords)
    {
        switch(coords)
        {
            case 0: return x;
            case 1: return y;
            case 2: return z;
            default: return 0.0f;
        }
    }

    public Vector3D abs()
    {
        return new Vector3D(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public Vector3D invert()
    {
        return new Vector3D(-x, -y, -z);
    }

    public float length()
    {
        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    public Vector3D normalize()
    {
        float oneOverLength = (1.0f/length());
        return new Vector3D(x*oneOverLength, y*oneOverLength, z*oneOverLength);
    }

    public Vector3D add(Vector3D vec) { return new Vector3D(x + vec.x, y + vec.y, z + vec.z); }

    public Vector3D sub(Vector3D vec)
    {
        return new Vector3D(x - vec.x, y - vec.y, z - vec.z);
    }

    public Vector3D add(float constant) {return new Vector3D(x + constant, y + constant, z + constant); }

    public Vector3D sub(float constant) {return new Vector3D(x - constant, y - constant, z - constant); }

    public Vector3D mul(int constant)
    {
        return new Vector3D(x * constant, y * constant, z *constant);
    }

    public Vector3D cross(Vector3D vec)
    {
        float _x = y * vec.z - z * vec.y;
        float _y = z * vec.x - x * vec.z;
        float _z = x * vec.y - y * vec.x;
        return new Vector3D(_x, _y, _z);
    }

    public float dot(Vector3D vec)
    {
        return x * vec.x + y * vec.y + z * vec.z;
    }

    public static Vector3D getBoundingBoxMin(Vector3D v1, Vector3D v2, Vector3D v3)
    {
        float minX = Math.min(Math.min(v1.x, v2.x), v3.x);
        float minY = Math.min(Math.min(v1.y, v2.y), v3.y);
        float minZ = Math.min(Math.min(v1.z, v2.z), v3.z);
        return new Vector3D(minX, minY, minZ);
    }

    public static Vector3D getBoundingBoxMax(Vector3D v1, Vector3D v2, Vector3D v3)
    {
        float maxX = Math.max(Math.max(v1.x, v2.x), v3.x);
        float maxY = Math.max(Math.max(v1.y, v2.y), v3.y);
        float maxZ = Math.max(Math.max(v1.z, v2.z), v3.z);
        return new Vector3D(maxX, maxY, maxZ);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3D vector3D = (Vector3D) o;

        if (Float.compare(vector3D.x, x) != 0) return false;
        if (Float.compare(vector3D.y, y) != 0) return false;
        return Float.compare(vector3D.z, z) == 0;

    }

    @Override
    public int hashCode()
    {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        return result;
    }
}
