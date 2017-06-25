import java.util.*;

public class School
{
	public ArrayList<Student> getSeniorsWithThreePointOrBetter(Student[] students)
	{
		ArrayList <Student> smartSeniors = new ArrayList();
		for(Student temp: students)
		{
			if(temp.getGPA() > 3 && temp.getCredits() >= 15 )
				smartSeniors.add(temp);
		}
		return smartSeniors;
	}
	
	// finds the student (or students) with 20 or more credits 
	// with the highest GPA, returns that student(s) in an ArrayList
	public ArrayList<Student> findValedictorian(ArrayList<Student> list)
	{
		ArrayList<Student> valedictorians = new ArrayList();
		valedictorians.add(list.get(0));
		for(int i = 1; i < list.size(); i++)
		{
			if(list.get(i).getCredits() > 20 && list.get(i).getGPA() > valedictorians.get(0).getGPA())
			{
				valedictorians.clear();
			}
			if(list.get(i).getCredits() > 20 && list.get(i).getGPA() >= valedictorians.get(0).getGPA())
				valedictorians.add(list.get(i));
		}
		return valedictorians;		
	}
	
	// Sorts the ArrayList into order by grade then within each grade by GPA
	public void sortListByGradeThenGPA(ArrayList<Student> list)
	{
		for(int i = 0; i < list.size() - 1; i++)
		{
			for(int j = i + 1; i < list.size(); j++)
			{
				if(Math.floor(list.get(j).getCredits() / 5) < Math.floor(list.get(j).getCredits() / 5) 
					|| (Math.floor(list.get(j).getCredits() / 5) == Math.floor(list.get(j).getCredits() / 5)
					&& list.get(j).getGPA() > list.get(i).getGPA() )) 
					//if the student is in a lower grade, or is in an equal grade and has a higher GPA
				{
					Student temp = list.get(j);
					list.set(j, list.get(i));
					list.set(i, temp);
				}
			}
		}
	}
	
}
