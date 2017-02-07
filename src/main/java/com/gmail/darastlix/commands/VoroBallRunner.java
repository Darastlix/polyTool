package com.gmail.darastlix.commands;

import com.gmail.darastlix.data.Vector3D;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class VoroBallRunner extends BukkitRunnable
{

    private World world;
    private Vector3D center;
    private int radius;
    private int numberOfPoints;
    private Set<Vector3D> borderPoints;

    public VoroBallRunner(World world, Vector3D center, int radius, int numberOfPoints)
    {
        this.world = world;
        this.center = center;
        this.radius = radius;
        this.numberOfPoints = numberOfPoints;
        borderPoints = new HashSet<>();
    }

    @Override
    public void run()
    {
        int radius_squared = radius * radius;
        List<Vector3D> voronoiPoints = generateRandomPoints(center, radius, numberOfPoints);

        //find all border points
        for (int i = (int) center.x - (radius + 1); i < (int) center.x + (radius + 1); i++)
        {
            for (int j = (int) center.y - (radius + 1); j < (int) center.y + (radius + 1); j++)
            {
                for (int k = (int) center.z - (radius + 1); k < (int) center.z + (radius + 1); k++)
                {
                    float dis = (float) (Math.pow((i - center.x), 2) + Math.pow((j - center.y), 2) + Math.pow((k - center.z), 2));
                    if (Math.abs(dis / radius_squared) >= 0.85f && dis <= radius_squared) //border
                    {
                        float minDistance = Float.MAX_VALUE;
                        Vector3D voronoiBorderPoint = new Vector3D();

                        for (Vector3D point : voronoiPoints)
                        {
                            float dX = (i - point.x);
                            float dY = (j - point.y);
                            float dZ = (k - point.z);
                            float _dis = dX * dX + dY * dY + dZ * dZ;
                            if (_dis < minDistance)
                            {
                                minDistance = _dis;
                                voronoiBorderPoint = point;
                            }
                        }
                        borderPoints.add(voronoiBorderPoint);
                    }
                }
            }
        }

        //fill the blocks
        for (int i = (int) center.x - (radius + 1); i < (int) center.x + (radius + 1); i++)
        {
            for (int j = (int) center.y - (radius + 1); j < (int) center.y + (radius + 1); j++)
            {
                for (int k = (int) center.z - (radius + 1); k < (int) center.z + (radius + 1); k++)
                {
                    float dis = (float) (Math.pow((i - center.x), 2) + Math.pow((j - center.y), 2) + Math.pow((k - center.z), 2));
                    if (dis <= radius_squared) //border
                    {
                        float minDistance = Float.MAX_VALUE;
                        Vector3D voronoiBorderPoint = new Vector3D();

                        for (Vector3D point : voronoiPoints)
                        {
                            float dX = (i - point.x);
                            float dY = (j - point.y);
                            float dZ = (k - point.z);
                            float _dis = dX * dX + dY * dY + dZ * dZ;
                            if (_dis < minDistance)
                            {
                                minDistance = _dis;
                                voronoiBorderPoint = point;
                            }
                        }
                        if (!borderPoints.contains(voronoiBorderPoint))
                        {
                            world.getBlockAt(i, j, k).setType(Material.STONE);
                        }
                    }
                }
            }
        }
    }

    private List<Vector3D> generateRandomPoints(Vector3D center, int radius, int number)
    {
        List<Vector3D> points = new LinkedList<>();
        for (int i = 0; i < number; i++)
            points.add(generateUniformPointInsideSphere(center, radius));
        return points;
    }

    private Vector3D generateUniformPointInsideSphere(Vector3D center, int radius)
    {
        double u = Math.random();
        double v = Math.random();
        double randX = Math.random() * radius;
        double randY = Math.random() * radius;
        double randZ = Math.random() * radius;
        double theta = 2 * Math.PI * u;
        double phi = Math.acos(2 * v - 1);
        float x = (float) (center.x + (randX * Math.sin(phi) * Math.cos(theta)));
        float y = (float) (center.y + (randY * Math.sin(phi) * Math.sin(theta)));
        float z = (float) (center.z + (randZ * Math.cos(phi)));

        return new Vector3D(x, y, z);
    }
}
