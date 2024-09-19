import React from 'react'
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { FileText, CreditCard, Bell } from 'lucide-react'

export function HomePage() {
    return (
        <div className="space-y-6 bg-gray-100 dark:bg-gray-900 text-gray-800 dark:text-gray-200">
            <h1 className="text-3xl font-bold">Welcome to Harmony Home Net</h1>

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                <Card className="bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100">
                    <CardHeader className="flex flex-row items-center space-x-2">
                        <FileText className="h-6 w-6 text-blue-600 dark:text-blue-400" />
                        <CardTitle>Recent Documents</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <ul className="space-y-2">
                            <li>Lease Agreement</li>
                            <li>House Rules</li>
                            <li>Maintenance Schedule</li>
                        </ul>
                    </CardContent>
                </Card>

                <Card className="bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100">
                    <CardHeader className="flex flex-row items-center space-x-2">
                        <CreditCard className="h-6 w-6 text-green-600 dark:text-green-400" />
                        <CardTitle>Upcoming Payments</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <ul className="space-y-2">
                            <li>Rent - Due in 5 days</li>
                            <li>Utilities - Due in 12 days</li>
                        </ul>
                    </CardContent>
                </Card>

                <Card className="bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100">
                    <CardHeader className="flex flex-row items-center space-x-2">
                        <Bell className="h-6 w-6 text-yellow-600 dark:text-yellow-400" />
                        <CardTitle>Recent Announcements</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <ul className="space-y-2">
                            <li>Community Picnic - This Saturday</li>
                            <li>Maintenance: Water Shut-off - Next Tuesday</li>
                        </ul>
                    </CardContent>
                </Card>
            </div>
        </div>
    )
}